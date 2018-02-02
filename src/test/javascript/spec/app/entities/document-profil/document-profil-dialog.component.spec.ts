/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CvthequeTestModule } from '../../../test.module';
import { DocumentProfilDialogComponent } from '../../../../../../main/webapp/app/entities/document-profil/document-profil-dialog.component';
import { DocumentProfilService } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.service';
import { DocumentProfil } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.model';
import { ShareProfilService } from '../../../../../../main/webapp/app/entities/share-profil';
import { RecrutementProfilService } from '../../../../../../main/webapp/app/entities/recrutement-profil';

describe('Component Tests', () => {

    describe('DocumentProfil Management Dialog Component', () => {
        let comp: DocumentProfilDialogComponent;
        let fixture: ComponentFixture<DocumentProfilDialogComponent>;
        let service: DocumentProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [DocumentProfilDialogComponent],
                providers: [
                    ShareProfilService,
                    RecrutementProfilService,
                    DocumentProfilService
                ]
            })
            .overrideTemplate(DocumentProfilDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentProfilDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DocumentProfil(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.documentProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'documentProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DocumentProfil();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.documentProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'documentProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
