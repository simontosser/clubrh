/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CvthequeTestModule } from '../../../test.module';
import { ShareProfilDialogComponent } from '../../../../../../main/webapp/app/entities/share-profil/share-profil-dialog.component';
import { ShareProfilService } from '../../../../../../main/webapp/app/entities/share-profil/share-profil.service';
import { ShareProfil } from '../../../../../../main/webapp/app/entities/share-profil/share-profil.model';
import { CompanyService } from '../../../../../../main/webapp/app/entities/company';

describe('Component Tests', () => {

    describe('ShareProfil Management Dialog Component', () => {
        let comp: ShareProfilDialogComponent;
        let fixture: ComponentFixture<ShareProfilDialogComponent>;
        let service: ShareProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [ShareProfilDialogComponent],
                providers: [
                    CompanyService,
                    ShareProfilService
                ]
            })
            .overrideTemplate(ShareProfilDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShareProfilDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShareProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShareProfil(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.shareProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shareProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShareProfil();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.shareProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shareProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
