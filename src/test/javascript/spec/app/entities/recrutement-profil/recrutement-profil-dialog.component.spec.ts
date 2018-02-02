/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CvthequeTestModule } from '../../../test.module';
import { RecrutementProfilDialogComponent } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil-dialog.component';
import { RecrutementProfilService } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.service';
import { RecrutementProfil } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.model';
import { CompanyService } from '../../../../../../main/webapp/app/entities/company';

describe('Component Tests', () => {

    describe('RecrutementProfil Management Dialog Component', () => {
        let comp: RecrutementProfilDialogComponent;
        let fixture: ComponentFixture<RecrutementProfilDialogComponent>;
        let service: RecrutementProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [RecrutementProfilDialogComponent],
                providers: [
                    CompanyService,
                    RecrutementProfilService
                ]
            })
            .overrideTemplate(RecrutementProfilDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecrutementProfilDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecrutementProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RecrutementProfil(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.recrutementProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recrutementProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RecrutementProfil();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.recrutementProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recrutementProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
